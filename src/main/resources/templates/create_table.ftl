// ����� ���� � �����
DROP TABLE IF EXISTS ${tableName};
CREATE TABLE IF NOT EXISTS ${tableName} (
<#list attrs as attr>
    ${nameOfAttr(attr)}
</#list>

    PRIMARY KEY(role, user_right)
);