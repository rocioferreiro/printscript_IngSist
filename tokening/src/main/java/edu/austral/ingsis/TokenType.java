package edu.austral.ingsis;

public interface TokenType {

    TokenType invalid = new TokenType() {
        @Override
        public String getRegex() {
            return "";
        }

        @Override
        public String getCategory() {
            return "invalid";
        }

        @Override
        public String getName(){
            return "invalid";
        }

        @Override
        public int getOrdinal() {
            return -1;
        }
    };

    String getRegex();
    String getCategory();
    String getName();
    int getOrdinal();
}
