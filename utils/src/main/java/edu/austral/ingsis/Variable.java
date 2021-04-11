package edu.austral.ingsis;

public interface Variable {

  Variable EMPTY =
      new Variable() {
        @Override
        public String getName() {
          return null;
        }

        @Override
        public VariableType getType() {
          return new VariableType("invalid", -1);
        }

        @Override
        public String getValue() {
          return null;
        }

        @Override
        public boolean isConst() {
          return false;
        }

        @Override
        public void setName(String name) {}

        @Override
        public void setType(VariableType type) {}

        @Override
        public void setValue(String value) {}

        @Override
        public void setIsConst(boolean isConst) {}
      };

  String getName();

  VariableType getType();

  String getValue();

  boolean isConst();

  void setName(String name);

  void setType(VariableType type);

  void setValue(String value);

  void setIsConst(boolean isConst);
}
