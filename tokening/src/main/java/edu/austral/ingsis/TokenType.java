package edu.austral.ingsis;

public interface TokenType {

  TokenType invalid =
      new TokenType() {
        @Override
        public String getRegex() {
          return "";
        }

        @Override
        public String getCategory() {
          return "invalid";
        }

        @Override
        public String getName() {
          return "invalid";
        }

        @Override
        public int getOrdinal() {
          return -1;
        }

          @Override
          public boolean isAble() {
              return true;
          }

          @Override
          public void setAble(boolean isAble) {

          }
      };

  String getRegex();

  String getCategory();

  String getName();

  int getOrdinal();

  boolean isAble();

  void setAble(boolean isAble);
}
