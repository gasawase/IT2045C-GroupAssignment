package com.bank;
import com.bank.Account;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AccountSerializer implements JsonSerializer<Account>, JsonDeserializer<Account> {

    @Override
    public JsonElement serialize(Account account, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(account.getClass().getSimpleName()));
        result.add("properties", jsonSerializationContext.serialize(account,account.getClass()));
        return result;
    }

    @Override
    public Account deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String accountType = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            // hacky namespace workaround since we are in com.bank whereas the example doesn't use any namespaces
            return jsonDeserializationContext.deserialize(element, Class.forName("com.bank." + accountType));

        } catch (ClassNotFoundException e) {

            throw new JsonParseException(e);
        }
    }
}
