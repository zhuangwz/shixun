package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

import java.util.Vector;
/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {
	private ArrayList<JigsawNode> list = new ArrayList<JigsawNode>();

    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     * 找出所有与节点p相邻且未被访问过的节点
     *
     */
    private Vector<JigsawNode> findNext(JigsawNode p){
    	Vector<JigsawNode> vec = new Vector<JigsawNode>();
    	for(int i=0;i<4;i++){
    		JigsawNode temp = new JigsawNode(p);
    		if(temp.move(i) && !exploreList.contains(temp) && !list.contains(temp)){
    			vec.add(temp);
    		}
    	}
    	return vec;
    }

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
        exploreList = new LinkedList<JigsawNode>();
        visitedList = new HashSet();
        list = new ArrayList<JigsawNode>();
        currentJNode = bNode;
        beginJNode = bNode;
        endJNode = eNode;
        exploreList.offer(currentJNode);
        //list.add(currentJNode);

        while(list.peek() != null){
        	currentJNode = exploreList.poll();
        	visitedList.add(currentJNode);
        	list.add(currentJNode);
        	if(currentJNode.equals(endJNode)){
        		JigsawNode p = currentJNode;
        		while(p != null){
        			solutionPath.insertElementAt(p,0);
        			p = p.getParent();
        		}
        		break;
        	}

        	searchedNodesNum++;
        	Vector<JigsawNode> next = findNext(currentJNode);
        	while(!next.empty()){
        		exploreList.offer(next.firstElement());
        		next.remove(0);
        	}

        }
    }


    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) {
        int s = 0; // 后续节点不正确的数码个数
        int dimension = JigsawNode.getDimension();
        for (int index = 1; index < dimension * dimension; index++) {
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                s++;
            }
        }

        int manhattanDistance = 0;
        for(int currentIndex = 1; currentIndex < dimension * dimension; currentIndex++){
        	for(int targerIndex = currentIndex;targerIndex <= dimension * dimension; targerIndex++){
        		int temp = jNode.getNodesState()[currentIndex];
        		if(temp != 0 && temp == endJNode.getNodesState()[targerIndex]){
        			int x1 = currentIndex % dimension;
        			int x2 = targerIndex % dimension;
        			int y1 = currentIndex / dimension;
        			int y2 = targerIndex / dimension;
        			int dx = Math.abs(x1 - x2);
        			int dy = Math.abs(y1 - y2);
        			manhattanDistance += (dx + dy);
        			break;
        		}
        	}
        }

        jNode.setEstimatedValue(20 * s + 30 * manhattanDistance);

    }
}
