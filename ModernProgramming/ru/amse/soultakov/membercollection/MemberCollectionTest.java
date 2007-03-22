package ru.amse.soultakov.membercollection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AccessibleObject;

public class MemberCollectionTest {

	@Marker
	@Retention(RetentionPolicy.RUNTIME)
	@interface Marker {		
	}
	
	@Marker
	public int field;
	
	@Marker
	public static void main(String[] args) {
		System.out.println(
			MemberCollection.getAnnotatedMembers(MemberCollectionTest.class, 
					Marker.class));
		System.out.println();
		MemberCollection mc = new MemberCollection(MemberCollectionTest.class);
		for(AccessibleObject o : mc) {
			System.out.println(o);
		}
		System.out.println();
		MemberCollection mc2 = new MemberCollection(String.class);
		for(AccessibleObject o : mc2) {
			System.out.println(o);
		}
		
	}

}
