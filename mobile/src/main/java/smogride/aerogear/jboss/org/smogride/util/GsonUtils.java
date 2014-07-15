package smogride.aerogear.jboss.org.smogride.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class GsonUtils {
    private GsonUtils() {
    }


    private static final GsonBuilder builder;
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final Gson GSON;

    static {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                try {
                    return FORMAT.parse(json.getAsString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(FORMAT.format(src));
            }
        });
        GSON = builder.create();
    }

}