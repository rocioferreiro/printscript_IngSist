package edu.austral.ingsis;

public interface TokenType {

    TokenType invalid = new TokenType() {
        @Override
        public String getRegex() {
            return "";
        }

        @Override
        public String identifier() {
            return "invalid";
        }
    };

    String getRegex();
    String identifier();
}
