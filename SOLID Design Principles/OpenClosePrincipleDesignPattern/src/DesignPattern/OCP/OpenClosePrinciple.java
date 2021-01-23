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

public class OpenClosePrinciple {

	public static void main (String args[]) {
		System.out.println();
		Product apple=new	Product("Apple",Color.GREEN,Size.SAMLL);
		Product orange=new	Product("Orange",Color.GREEN,Size.LARGE);
		Product bannana=new	Product("Bannana",Color.RED,Size.MIDIUM);
		Product house=new	Product("House",Color.BLUE,Size.HUGE);		
		List<Product> products=List.of(apple,orange,bannana,house);
		ProductFilter pf = new ProductFilter();	
		System.out.println("Color Filter:");
		pf.filterByColor(products, Color.GREEN).
		forEach(p->System.out.println("Name of Product is "+p.name+" Color is "+p.color));

		
		
	}
}
