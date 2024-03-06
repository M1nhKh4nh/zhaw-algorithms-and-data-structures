package ch.zhaw.ads;

public class BracketServer implements CommandExecutor {
    private final ListStack stack = new ListStack();
    private String command;

    private int lastPosition = 0;

    @Override
    public String execute(String command) throws Exception {
        return checkBrackets(command) ? "OK" : "NOK";
    }

    boolean checkBrackets(String arg) {
        command = arg;
        lastPosition = 0;
        do {
            String bracket = getNextBracket();
            if(isOpeningBracket(bracket)) {
                stack.push(bracket);
            } else if(isClosingBracket(bracket)) {
                if(stack.isEmpty()) {
                    return false;
                }
                String lastOpeningBracket = stack.peek().toString();
                if(isMatching(lastOpeningBracket, bracket)) {
                    stack.pop();
                } else {
                    stack.removeAll();
                    return false;
                }
            }

        } while (lastPosition < command.length());

        if(stack.isEmpty()){
            return true;
        } else {
            stack.removeAll();
            return false;
        }
    }

    private String getNextBracket() {
        for(int i = lastPosition; i < command.length(); i++){
            if(Character.toString(command.charAt(i)).equals("/") || Character.toString(command.charAt(i)).equals("*")) {
                if(isOpeningBracket(command.charAt(i) + Character.toString(command.charAt(i + 1))) || isClosingBracket(command.charAt(i) + Character.toString(command.charAt(i + 1)))){
                    lastPosition = i + 2;
                    return command.charAt(i) + Character.toString(command.charAt(i + 1));
                }
            }

            if(isOpeningBracket(Character.toString(command.charAt(i))) || isClosingBracket(Character.toString(command.charAt(i)))){
                lastPosition = i + 1;
                return Character.toString(command.charAt(i));
            }
        }
        return "";
    }

    private boolean isOpeningBracket(String character) {
        return character.equals("(") || character.equals("{") || character.equals("[") || character.equals("<") || character.equals("/*");
    }

    private boolean isClosingBracket(String character) {
        return character.equals(")") || character.equals("}") || character.equals("]") || character.equals(">") || character.equals("*/");
    }

    private boolean isMatching(String openingBracket, String closingBracket) {
        return switch (openingBracket) {
            case "(" -> closingBracket.equals(")");
            case "{" -> closingBracket.equals("}");
            case "[" -> closingBracket.equals("]");
            case "<" -> closingBracket.equals(">");
            case "/*" -> closingBracket.equals("*/");
            default -> false;
        };
    }
}
