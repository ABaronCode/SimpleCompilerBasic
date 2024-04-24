import java.util.ArrayList;

public class StatementsNode extends StatementNode {

    //we are using array list because it is resizable
    static ArrayList<Node> statementNodeList = null;

    //constructor
    public StatementsNode() {
        statementNodeList = new ArrayList<>();
    }
    //since nothing is going to be returned we jsut want to build the AST inside this list here
    public void getStatementNodeList(Node stateAdd) {
        statementNodeList.add(stateAdd);
    }

    public static ArrayList<Node> getList() {
        return statementNodeList;
    }


    //for now since there is now format for statement node
    @Override
    public String toString() {
        return String.valueOf(statementNodeList);
    }
}
