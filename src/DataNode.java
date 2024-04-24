//takes a list of STRING, IntegerNode and FloatNode

import java.util.List;

public class DataNode extends StatementNode {

    private final List<Node> dataSet;

    public DataNode(List<Node> dataSet) { this.dataSet = dataSet;}


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Node data: dataSet) {
            sb.append(data);
        }
        return "DATANODE" + sb.toString() + ")";
    }
}

