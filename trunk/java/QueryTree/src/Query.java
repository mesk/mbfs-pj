import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Query {
	private static final String SELECT = "SELECT";
	private static final String FROM = "FROM";
	static CCJSqlParserManager parserManager = new CCJSqlParserManager();
	static List<String> slist = new ArrayList<String>();
	static Map<String, String> aliases = new HashMap<String, String>();
	static FromItem sfrom = null;
	static List<Join> sjoin = new ArrayList<Join>();

	public static void main(String[] args) throws JSQLParserException {
		Reader reader = null;
		try {
			if (args.length > 0) {
				reader = new BufferedReader(new FileReader(args[0]));
			} else {
				// InputStreamReader isr = new InputStreamReader(System.in);
				reader = new StringReader("SELECT myid AS MYID, mycol, tab.*, schema.tab.*, mytab.mycol2, myschema.mytab.mycol, myschema.mytab.* FROM mytable WHERE mytable.col = 9");
			}
			PlainSelect query = (PlainSelect) ((Select) parserManager
					.parse(reader)).getSelectBody();
			
			Tree<String> tree = new Tree<String>("QUERY");
			fillTree(tree, query, SELECT);

			System.out.println(tree);
			
			System.out.println("SUMMARY:");
			
			Tree<String> stree = new Tree<String>(SELECT);
			PlainSelect summary = new PlainSelect();
			for(Entry<String, String> entry : aliases.entrySet()){
				slist.remove(entry.getKey());
				if(slist.contains(entry.getValue())){
					slist.set(slist.indexOf(entry.getValue()), entry.getKey() + " as "+entry.getValue());
				}
			}
			summary.setSelectItems(slist);
			summary.setFromItem(sfrom);
			summary.setJoins(sjoin);
			fillTree(stree, summary, SELECT);
			System.out.println(stree);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void fillTree(Tree<String> tree, PlainSelect query, String key){
		@SuppressWarnings("unchecked")
		List<Object> list = query.getSelectItems();
		for(Object item : list){
			Object o = item.toString();
			if(String.valueOf(o).toUpperCase().startsWith("SUM")){
				o = "SUM(...) AS ";
				if(item instanceof SelectExpressionItem){
					o = o + ((SelectExpressionItem)item).getAlias();
				}
			}
			if(item instanceof SelectExpressionItem){
				SelectExpressionItem sei = ((SelectExpressionItem)item);
				Expression exp = sei.getExpression();
				if(exp instanceof Column || exp instanceof StringValue){
					String column = exp.toString();
					if(sei.getAlias() != null){
						aliases.put(column, sei.getAlias());
					}
					if(!slist.contains(column)){
						slist.add(column);
					}
				}
			}
			tree.addLeaf(key, String.valueOf(o));
		}
		FromItem from = query.getFromItem();
		fillTree(tree, from, key);
		@SuppressWarnings("unchecked")
		List<Join> joins = query.getJoins();
		if(joins!=null && joins.size()>0){
			for(Join join : new ArrayList<Join>(joins)){
				fillTree(tree, join.getRightItem(), null);
			}
		}
	}
	private static void fillTree(Tree<String> tree, FromItem from, String key){
		if(from instanceof SubSelect){
			if(key!=null){
				tree.addLeaf(key, FROM);
			}
			tree.addLeaf("FROM", from.getAlias());
			SubSelect subs = ((SubSelect)from);
			PlainSelect sub = ((PlainSelect)subs.getSelectBody());
			fillTree(tree, sub, from.getAlias());
		}else{
			if(key!=null){
				tree.addLeaf(key, FROM);
			}
			tree.addLeaf("FROM", String.valueOf(from));
			if(sfrom == null){
				sfrom = from;
			}else {
				Join join = new Join();
				join.setRightItem(from);
				if(!sjoin.contains(join)){
					sjoin.add(join);
				}
			}
		}
	}
}
