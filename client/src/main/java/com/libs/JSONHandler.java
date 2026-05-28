package com.libs;

// imports for JSON
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

// imports for the Java in out stuff
import java.io.File;
import java.io.IOException;

// imports for Data
import com.backend.Data;

public class JSONHandler {
    // Creating object for Object Mapper
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String FILENAME = "Data.json"; // file name

    // ensures that the JSON file exists. Throws an IO expection if there are any problems.
    public static void ensureDataExists() throws IOException {
        File f = new File(FILENAME);
        if (f.exists()) return;

        ObjectNode root = MAPPER.createObjectNode(); // the whole Data.json list itself
        ObjectNode UserData = MAPPER.createObjectNode(); // User Data
        ObjectNode TargetInfo = MAPPER.createObjectNode(); // Target Info
        ObjectNode Internal = MAPPER.createObjectNode(); // Internal
        /*test.put("number", 50);
        root.set("test", test);*/
        UserData.put("uuid", "none");
        UserData.put("username", "none");
        TargetInfo.put("ip", "none");
        TargetInfo.put("port", 0);
        TargetInfo.put("server", false);
        Internal.put("port", 1773);
        Internal.put("ip", "0.0.0.0");
        root.set("UserData", UserData);
        root.set("TargetInfo", TargetInfo);
        root.set("Internal", Internal);


        MAPPER.writerWithDefaultPrettyPrinter().writeValue(f, root);
    }

    // Read JSON from the Data.json file
    public static String readJSONValue(String category, String child) throws IOException {
        // category is the list that the child is under.
        // this is referring to Data.json
        File f = new File(FILENAME);

        ensureDataExists(); // checking to make sure Data.json exists
        JsonNode rootNode = MAPPER.readTree(f); //mapping the Data.json
        String name = rootNode.path(category).get(child).asText(); // collecting the value
        return name;
        }

        // writes the specified value to the child inside a category. For use with Data.json
    public static void writeJSONValue(String category, String child, String type, String value) throws IOException {
        File f = new File(FILENAME);
        JsonNode root = MAPPER.readTree(f); // creates a map of the JSON
        ObjectNode rootObj = (ObjectNode) root; // turns the map into a mutable object we can navigate
        /*if (!(root instanceof ObjectNode)) { 
            throw new IllegalStateException("Root is not an object");
        }*/
        JsonNode catNode = rootObj.get(category); // maps the selected category

        // creating the object for the Category
        ObjectNode catObj;
        if (catNode instanceof ObjectNode) {
            catObj = (ObjectNode) catNode;
        } else {
            catObj = MAPPER.createObjectNode();
            rootObj.set(category, catObj);
        }

        //creating the object for the child.
        /* JsonNode chilNode = catObj.get(child);
        ObjectNode chilObj;
        if (chilNode instanceof ObjectNode) {
            chilObj = (ObjectNode) chilNode;
        } else {
            chilObj = MAPPER.createObjectNode();
            catObj.set(child, chilObj);
        } */

        switch(type) {
            case "int": {
                catObj.put(child, Integer.parseInt(value));
                break;
            }
            case "bool": {
                catObj.put(child, Boolean.parseBoolean(value));
                break;
            }
            default: {
                catObj.put(child, value);
                break;
            }
        }
        // write the new JSON
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(f, rootObj);
    }

    // JSON to Java Synchronization
    public static void writeJsonToJava() {
        try {
            // User Data
            Data.STORE.userData.uuid = readJSONValue("UserData", "uuid");
            Data.STORE.userData.username = readJSONValue("UserData", "username");
            // Target Info
            Data.STORE.targetInfo.ip = readJSONValue("TargetInfo","ip");
            Data.STORE.targetInfo.port = Integer.parseInt(readJSONValue("TargetInfo", "port"));
            Data.STORE.targetInfo.server = Boolean.parseBoolean(readJSONValue("TargetInfo", "server"));
            // Internal
            Data.STORE.internal.ip = readJSONValue("Internal", "ip");
            Data.STORE.internal.port = Integer.parseInt(readJSONValue("Internal", "port"));
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    // Java to JSON Synchronization
    public static int writeJavaToJSON() {
        // note: make this auto-expandable in the future (instead of adding each individual value)
        try {
            // User Data
            writeJSONValue("UserData", "uuid", "str", Data.STORE.userData.uuid);
            writeJSONValue("UserData", "username", "str", Data.STORE.userData.username);
            // TargetInfo
            writeJSONValue("TargetInfo", "ip", "str", Data.STORE.targetInfo.ip);
            writeJSONValue("TargetInfo", "port", "int", Integer.toString(Data.STORE.targetInfo.port));
            writeJSONValue("TargetInfo", "server", "bool", Boolean.toString(Data.STORE.targetInfo.server));
            // Internal
            writeJSONValue("Internal", "port", "int", Integer.toString(Data.STORE.internal.port));
            writeJSONValue("Internal", "ip", "str", Data.STORE.internal.ip);
        } catch (IOException e) {
            System.err.println(ScreenLib.Colors.YELLOW+"WARNING: Funtion 'writeJavatoJSON' has failed unexpectedly. The program may continue, but Data may not be stored externally (in JSON). \nTo ensure functionality, please restart the program via Ctrl + C."+ScreenLib.Colors.RESET);
            System.err.println(e);
            System.err.println(ScreenLib.Colors.YELLOW+"WARNING: Funtion 'writeJavatoJSON' has failed unexpectedly. The program may continue, but Data may not be stored externally (in JSON). \nTo ensure functionality, please restart the program via Ctrl + C."+ScreenLib.Colors.RESET);
            return 1;
        }
        return 0;
    }
}
