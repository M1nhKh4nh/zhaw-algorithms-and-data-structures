package ch.zhaw.ads;

public class WellformedXmlServer implements CommandExecutor{
    private final ListStack stack = new ListStack();
    private String xml;
    private int closingTagIndex = 0;
    @Override
    public String execute(String command) throws Exception {
        return checkWellformed(command) ? "OK" : "NOK";
    }

    boolean checkWellformed(String arg) {
        stack.removeAll();
        closingTagIndex = 0;
        xml = arg;
        do {
            String token = getNextToken();
            if(!token.endsWith("/>")) {
                if (!token.contains("/")) {
                    if(token.contains(" ")) {
                        int indexOAttribute = token.indexOf(" ");
                        int closingTagIndex = token.indexOf(">");
                        String attribute = token.substring(indexOAttribute, closingTagIndex);
                        token = token.replaceAll(attribute, "");
                    }
                    stack.push(token);
                } else {
                    if(!stack.isEmpty()) {
                        String lastToken = stack.peek().toString();
                        return token.replaceFirst("/", "").equals(lastToken);
                    } else {
                        return false;
                    }
                }
            }

            xml = xml.substring(closingTagIndex + 1);
        } while (!xml.isBlank());
        return stack.isEmpty();
    }

    private String getNextToken() {
        int openTagIndex = xml.indexOf("<");
        closingTagIndex = xml.indexOf(">");
        return xml.substring(openTagIndex, closingTagIndex + 1);
    }
}
