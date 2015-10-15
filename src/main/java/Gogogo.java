import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import util.ProcessEClassUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMufazzalov on 15.10.2015.
 */
public class Gogogo {
    static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    public static void main(String[] args) throws IOException, TemplateException, URISyntaxException {

        cfg.setClassForTemplateLoading(Gogogo.class, "/templates/");

        Template temp = cfg.getTemplate("test.ftl");

        Writer out = new OutputStreamWriter(System.out);
        temp.process(new DataModel(), out);

        readEcoreFiles();
    }

    private static void readEcoreFiles() throws IOException, URISyntaxException {

        List<Path> files = new ArrayList<Path>();
        Path rootPath = Paths.get(Gogogo.class.getResource("").toURI());

        getEcoreFiles(files, rootPath);

        files.stream().forEach(f -> {
            XMIResource resource = new XMIResourceImpl(URI.createFileURI(f.toFile().getPath()));
            try {
                resource.load(null);
                resource.getContents().stream().forEach(c -> {
                    if (c instanceof EPackage) {
                        processPackge((EPackage) c);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static void processPackge(EPackage ePackage) {
        // бежим по классам
        ePackage.getEClassifiers().stream().forEach(s -> {
            if (s instanceof EClassImpl) {
                System.out.println(s.getName());
                processClass((EClassImpl) s);
            }
        });
    }

    private static void processClass(EClassImpl eCls) {
        eCls.getEAllAttributes().stream().forEach(s -> {
            System.out.println("--" + s.getName());
        });


        Template temp = null;
        try {
            temp = cfg.getTemplate("create_table.ftl");

            Writer out = new FileWriter(eCls.getName() + ".cql");
            temp.process(new ProcessEClassUtil(eCls), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * получить рекурсивно все файлы ecore
     *
     * @param files
     * @param path
     * @throws IOException
     */
    private static void getEcoreFiles(List<Path> files, Path path) throws IOException {
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path entry : stream) {
            if (Files.isDirectory(entry)) {
                getEcoreFiles(files, entry);
            } else if (FilenameUtils.getExtension(entry.getFileName().toString().toLowerCase()).endsWith("ecore")) {
                files.add(entry);
            }
        }
    }
}
