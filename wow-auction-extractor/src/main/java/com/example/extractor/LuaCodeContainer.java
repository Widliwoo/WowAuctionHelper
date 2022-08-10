package com.example.extractor;

/**
 * Класс содержит код экспортёра данных на Lua.
 * Сделал этот класс для обхода проблемы при чтении кода из файла службой.
 */
public class LuaCodeContainer {

    public static final String LUA_EXTRACT_DATA = "return exportJson(AUCTIONATOR_PRICE_DATABASE)";
    public static final String LUA_EXTRACTING_FUNCTIONS = "local function escapeCharSpecialChars(str)\n" +
            "  return string.gsub(str, \"\\\"\", \"\\\\\\\"\");\n" +
            "end\n" +
            "\n" +
            "local function convertProducts(products)\n" +
            "  local result = {};\n" +
            "  for k, v in pairs(products) do\n" +
            "    local product = {\n" +
            "        name = escapeCharSpecialChars(k),\n" +
            "        price = v\n" +
            "    };\n" +
            "    table.insert(result, product);\n" +
            "  end\n" +
            "  return result;\n" +
            "end\n" +
            "\n" +
            "local function convertRealms(r)\n" +
            "  local realms = {};\n" +
            "  for k, v in pairs(r) do\n" +
            "    if string.find(k, \"3.3.5a\") then\n" +
            "      local realm = {\n" +
            "        name = escapeCharSpecialChars(k),\n" +
            "        products = convertProducts(v)\n" +
            "      };\n" +
            "      table.insert(realms, realm);\n" +
            "    end\n" +
            "  end\n" +
            "  return realms;\n" +
            "end\n" +
            "\n" +
            "exportJson = function (sourceTable) \n" +
            "  local realms = convertRealms(sourceTable);\n" +
            "  local realmJS = {};\n" +
            "  for _, v in pairs(realms) do\n" +
            "    local productJS = {};\n" +
            "    for _, v2 in pairs(v[\"products\"]) do\n" +
            "      table.insert(productJS, string.format(\"{\\\"name\\\": \\\"%s\\\", \\\"price\\\": \\\"%s\\\"}\", v2[\"name\"], v2[\"price\"]))\n" +
            "    end\n" +
            "    local productJSON = table.concat(productJS, \",\");\n" +
            "    table.insert(realmJS, string.format(\"{\\\"name\\\": \\\"%s\\\", \\\"products\\\": [%s]}\", v[\"name\"], productJSON));\n" +
            "  end\n" +
            "return \"[\" .. table.concat(realmJS, \", \") .. \"]\";\n" +
            "end\n";
}
