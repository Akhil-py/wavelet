import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Enter query in the URL to store or " +  
                    "search for a string\n\nSyntax for add: /add?s=itemToAdd" + 
                    "\nSyntax for search: /search?s=itemToSearch");
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format("Item %s added to the list!", 
                            parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String query = "\n";
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).contains(parameters[1])) {
                            query += list.get(i) + "\n";
                        }
                    }
                    return String.format("You searched for %s\n\n\n" + 
                            "Search Results:\n%s", parameters[1], query);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
