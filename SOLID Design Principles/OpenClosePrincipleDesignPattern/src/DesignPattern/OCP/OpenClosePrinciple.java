package DesignPattern.OCP;

import java.util.List;
import java.util.stream.Stream;

enum Color{
	RED,GREEN,BLUE
}

enum Size{
	SAMLL,MIDIUM,LARGE,HUGE
}
class Product{
	
	public String name;
	public Color color;	
	public Size size;
	
	Product(String name,Color color,Size size){
		this.name=name;
		this.color=color;
		this.size=size;		
	}
}
class ProductFilter{
	public Stream<Product> filterByColor(List<Product> products,Color color){
		return products.stream().filter(p->p.color==color);
	}
	public Stream<Product> filterBySize(List<Product> products,Size size){
		return products.stream().filter(p->p.size==size);
	}
	public Stream<Product> filterByColorAndSize(List<Product> products,Color color,Size size){
		return products.stream().filter(p->p.size==size && p.color==color);
	}
	
}


interface Specification<T>{
	boolean isSatisfied(T item);
}
interface Filter<T>{
	Stream<T> filter(List<T> items,Specification<T> spec );
}
class ColorSpecification implements Specification<Product>{

	private Color color;
	public ColorSpecification(Color color){
		this.color=color;
	} 
	
	@Override
	public boolean isSatisfied(Product item) {		
		return color==item.color;
	}	
}
class SizeSpecification implements Specification<Product>{

	private Size size;
	SizeSpecification(Size size){
		this.size=size;
	} 
	
	@Override
	public boolean isSatisfied(Product item) {		
		return this.size==item.size;
	}	
}
class AndSpecifcation<T> implements Specification<T>{

	private Specification<T> first,second;
	
	public AndSpecifcation(Specification<T> first,Specification<T> second){
		this.first=first;
		this.second=second;
	}
	@Override
	public boolean isSatisfied(T item) {		
		return first.isSatisfied(item) && second.isSatisfied(item);
	}
	
}
class BetterFilter implements Filter<Product>{
	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec) {		
		return items.stream().filter(p-> spec.isSatisfied(p));
	}	
}

public class OpenClosePrinciple {

	public static void main (String args[]) {		
		Product apple=new	Product("Apple",Color.GREEN,Size.SAMLL);
		Product orange=new	Product("Orange",Color.GREEN,Size.LARGE);
		Product bannana=new	Product("Bannana",Color.RED,Size.MIDIUM);
		Product house=new	Product("House",Color.BLUE,Size.HUGE);
		Product tree=new	Product("Tree",Color.BLUE,Size.LARGE);	
		List<Product> products=List.of(apple,orange,bannana,house,tree);
		ProductFilter pf = new ProductFilter();	
		System.out.println("Color Filter(OLD):");
		pf.filterByColor(products, Color.GREEN).
		forEach(p->System.out.println("Name of Product is "+p.name+" Color is "+p.color));
		
		pf.filterBySize(products, Size.LARGE).
		forEach(p->System.out.println("Name of Product is "+p.name+" Size is "+p.size));
		
		System.out.println("Better Color Filter(NEW):");
		
		BetterFilter bf=new BetterFilter();
		bf.filter(products, new ColorSpecification(Color.GREEN)).
				forEach(p->System.out.println("Product Name of Green Color is "+p.name));
		
		System.out.println("Better Size  Filter(NEW):"); 
		bf.filter(products, new SizeSpecification(Size.LARGE)).
		forEach(p->System.out.println("Product Name of Large is "+p.name));
		
//		products.forEach(p-> System.out.println("Product Name:"+p.name+" Color:"+p.color+" Size:"+p.size));
		System.out.println("Large Blue Filter(NEW):");	
		
		
		bf.filter(products, 
				new AndSpecifcation< >(
						new ColorSpecification(Color.BLUE),
						new SizeSpecification(Size.LARGE))).
				forEach(p  ->System.out.println("Product Name of Large Blue is "+p.name));
		
	}
}
