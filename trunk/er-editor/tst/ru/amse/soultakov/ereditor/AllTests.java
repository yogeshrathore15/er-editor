/*
 * Created on 07.03.2007
 */
package ru.amse.soultakov.ereditor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ru.amse.soultakov.ereditor.model.ArrayAttributeTypeTest;
import ru.amse.soultakov.ereditor.model.AttributeTest;
import ru.amse.soultakov.ereditor.model.CommentTest;
import ru.amse.soultakov.ereditor.model.DiagramTest;
import ru.amse.soultakov.ereditor.model.EntityTest;
import ru.amse.soultakov.ereditor.model.LinkTest;
import ru.amse.soultakov.ereditor.model.RelationshipTest;
import ru.amse.soultakov.ereditor.model.SimpleAttributeTypeTest;
import ru.amse.soultakov.ereditor.view.DiagramPresentationTest;

@RunWith(Suite.class)
@SuiteClasses(
        {AttributeTest.class,
         CommentTest.class,
         EntityTest.class,
         LinkTest.class,
         RelationshipTest.class,
         DiagramTest.class,
         DiagramPresentationTest.class,
         ArrayAttributeTypeTest.class,
         SimpleAttributeTypeTest.class})
public class AllTests {

}
