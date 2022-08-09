local function escapeCharSpecialChars(str)
  return string.gsub(str, "\"", "\\\"");
end

local function convertProducts(products)
  local result = {};
  for k, v in pairs(products) do
    local product = {
        name = escapeCharSpecialChars(k),
        price = v
    };
    table.insert(result, product);
  end
  return result;
end

local function convertRealms(r)
  local realms = {};
  for k, v in pairs(r) do
    if string.find(k, "3.3.5a") then
      local realm = {
        name = escapeCharSpecialChars(k),
        products = convertProducts(v)
      };
      table.insert(realms, realm);
    end
  end
  return realms;
end

exportJson = function (sourceTable) 
  local realms = convertRealms(sourceTable);
  local realmJS = {};
  for _, v in pairs(realms) do
    local productJS = {};
    for _, v2 in pairs(v["products"]) do
      table.insert(productJS, string.format("{\"name\": \"%s\", \"price\": \"%s\"}", v2["name"], v2["price"]))
    end
    local productJSON = table.concat(productJS, ",");
    table.insert(realmJS, string.format("{\"name\": \"%s\", \"products\": [%s]}", v["name"], productJSON));
  end
return "[" .. table.concat(realmJS, ", ") .. "]";
end
