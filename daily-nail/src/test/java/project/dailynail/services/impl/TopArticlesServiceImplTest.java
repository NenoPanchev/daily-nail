package project.dailynail.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.services.ArticleService;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TopArticlesServiceImplTest {
    private TopArticlesServiceImpl serviceToTest;
    private ArticleEntity first, second, third, fourth, fifth, sixth;

    @Mock
    private ArticleService mockArticleService;


    @BeforeEach
    void setUp() {
        first = (ArticleEntity) new ArticleEntity().setId("1");
        second = (ArticleEntity) new ArticleEntity().setId("2");
        third = (ArticleEntity) new ArticleEntity().setId("3");
        fourth = (ArticleEntity) new ArticleEntity().setId("4");
        fifth = (ArticleEntity) new ArticleEntity().setId("5");

        serviceToTest = new TopArticlesServiceImpl(mockArticleService);
        serviceToTest.add(first.getId());
        serviceToTest.add(second.getId());
        serviceToTest.add(third.getId());

    }

    @Test
    void testAdd() {
        assertEquals(3, serviceToTest.getTopArticlesIds().size());
        serviceToTest.add(fourth.getId());
        assertEquals(4, serviceToTest.getTopArticlesIds().size());
        assertEquals(serviceToTest.getTopArticlesIds().peekFirst(), "1");
        assertEquals(serviceToTest.getTopArticlesIds().peekLast(), "4");
        serviceToTest.add(fifth.getId());
        assertEquals(4, serviceToTest.getTopArticlesIds().size());
        assertEquals(serviceToTest.getTopArticlesIds().peekFirst(), "2");
        assertEquals(serviceToTest.getTopArticlesIds().peekLast(), "5");

    }

    @Test
    void testRemove() {
        serviceToTest.remove("1");
        assertEquals(2, serviceToTest.getTopArticlesIds().size());
        assertEquals(serviceToTest.getTopArticlesIds().peekFirst(), "2");
        assertEquals(serviceToTest.getTopArticlesIds().peekLast(), "3");
    }

    @Test
    void testToString() {
        String expected = "TopArticlesServiceImpl{}";
        String actual = serviceToTest.toString();
        assertEquals(expected, actual);
    }
}