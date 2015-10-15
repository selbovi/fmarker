package util;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.eclipse.emf.ecore.EAttribute;

import java.util.List;

/**
 * Created by SMufazzalov on 16.10.2015.
 */
public class NameOfAttr implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        StringModel attr = (StringModel) arguments.get(0);
        return attr.get("name");
    }
}
