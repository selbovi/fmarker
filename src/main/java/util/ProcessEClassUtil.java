package util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.impl.EClassImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SMufazzalov on 15.10.2015.
 */
public class ProcessEClassUtil {

    private EClassImpl eCls;

    public String getTableName() {
        return tableName;
    }

    private String tableName;

    public List<EAttribute> getAttrs() {
        return attrs;
    }

    List<EAttribute> attrs;

    private NameOfAttr nameOfAttr = new NameOfAttr();

    public NameOfAttr getNameOfAttr() {
        return nameOfAttr;
    }

    public ProcessEClassUtil(EClassImpl eCls) {
        assert eCls != null;
        this.eCls = eCls;
        this.attrs = eCls.getEAllAttributes();
        assert  eCls.getName() != null;
        this.tableName = eCls.getName();
    }

}
