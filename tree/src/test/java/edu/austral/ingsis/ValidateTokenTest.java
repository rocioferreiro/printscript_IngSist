package edu.austral.ingsis;


import edu.austral.ingsis.rules.ConcreteRule;
import edu.austral.ingsis.rules.RuleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class ValidateTokenTest {

  private final String regex = "DECLARATION,VARIABLE,DECLARE_TYPE,TYPE(,EQUAL,(((VALUE|VARIABLE),COMPARATOR,(VALUE|VARIABLE))|((VALUE|VARIABLE)((,OPERATOR,(VALUE|VARIABLE))?)+)))?";
  private final ConcreteRule rule = new ConcreteRule(RuleType.DECLARATION, regex);

  @Test
  public void testValidTokens() throws FileNotFoundException {
    String testDirectory = "src/test/resources/1.0/valid-tokens/";
    File srcPath = new File(testDirectory + "main.ps");
    String expectedOutput = TestHelper.readLines(testDirectory + "output.txt");
    String actualOutput = ASTSerializer.serializeASTWrapper(rule.validateTokens(ASTSerializer.deserializeTokens(srcPath)).get());
    Assertions.assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testInvalidTokens() {
    String testDirectory = "src/test/resources/1.0/invalid-tokens/";
    File srcPath = new File(testDirectory + "main.ps");
    Optional<ASTWrapper> wrapper = rule.validateTokens(ASTSerializer.deserializeTokens(srcPath));
    Assertions.assertTrue(wrapper.isEmpty());
  }
}
