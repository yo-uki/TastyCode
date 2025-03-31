package pl.youkidesign.TastyCode.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.youkidesign.TastyCode.entity.CustomerOrder;
import pl.youkidesign.TastyCode.entity.Drink;
import pl.youkidesign.TastyCode.entity.Food;
import pl.youkidesign.TastyCode.model.DrinkAdditions;
import pl.youkidesign.TastyCode.repository.CustomerOrderRepository;
import pl.youkidesign.TastyCode.repository.DrinkRepository;
import pl.youkidesign.TastyCode.repository.FoodRepository;
import pl.youkidesign.TastyCode.utils.UserInputProcess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TastyCodeOrderResourceTest {


    @Mock
    private FoodRepository foodRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @InjectMocks
    @Spy
    private TastyCodeOrderResource tastyCodeOrderResource;

    @Mock
    private UserInputProcess userInputProcess;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testOrderMainCourseNoOrder() {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        tastyCodeOrderResource.scanner = new Scanner(inputStream);

        Food result = tastyCodeOrderResource.orderMainCourse();

        assertNull(result);
    }

    @Test
    public void testPickDessertSuccessful() {
        Food dessert = new Food();
        dessert.setId(3L);
        dessert.setName("Ice Cream");
        dessert.setPrice(8.0);
        dessert.setDessert(true);

        List<Food> desserts = List.of(dessert);

        when(foodRepository.findByIsDessert(true)).thenReturn(desserts);

        String input = "3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        tastyCodeOrderResource.scanner = new Scanner(inputStream);

        Food result = tastyCodeOrderResource.pickDessert();

        assertNotNull(result);
        assertEquals("Ice Cream", result.getName());
        verify(foodRepository).findByIsDessert(true);
    }


    @Test
    public void testOrderDrinkNoOrder() {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        userInputProcess.scanner = new Scanner(inputStream);

        Drink result = tastyCodeOrderResource.orderDrink();

        assertNull(result);
    }

    @Test
    public void testMakeAnOrderSuccessful() {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setId(1L);
//        restaurant.setName("Italian Restaurant");
//        restaurant.setCuisineType("Italian");

        Food mainCourse = new Food();
        mainCourse.setId(1L);
        mainCourse.setName("Pasta");
        mainCourse.setPrice(18.0);
        mainCourse.setRestaurantId(1L);
        mainCourse.setDessert(false);

        Food dessert = new Food();
        dessert.setId(3L);
        dessert.setName("Tiramisu");
        dessert.setPrice(10.0);
        dessert.setDessert(true);

        Drink drink = new Drink();
        drink.setId(2L);
        drink.setName("Wine");
        drink.setPrice(12.0);
        drink.setContainsAlco(true);

        doReturn(mainCourse).when(tastyCodeOrderResource).orderMainCourse();
        doReturn(dessert).when(tastyCodeOrderResource).pickDessert();
        doReturn(drink).when(tastyCodeOrderResource).orderDrink();
        doReturn(DrinkAdditions.ICE.toString()).when(tastyCodeOrderResource).drinkAdditionRequest();

        String input = "Aleksandra\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        tastyCodeOrderResource.scanner = new Scanner(inputStream);

        tastyCodeOrderResource.makeAnOrder();

        verify(customerOrderRepository).save(any(CustomerOrder.class));
        verify(tastyCodeOrderResource).orderMainCourse();
        verify(tastyCodeOrderResource).pickDessert();
        verify(tastyCodeOrderResource).orderDrink();

        assertTrue(outContent.toString().contains("Ok Aleksandra, here's your order:"));
        assertTrue(outContent.toString().contains("Pasta, Tiramisu, Wine"));
        assertTrue(outContent.toString().contains("Total price = 40.0zł"));
    }

    @Test
    public void testMakeAnOrderNoFoodOnlyDrink() {
        Drink drink = new Drink();
        drink.setId(2L);
        drink.setName("Water");
        drink.setPrice(3.0);
        drink.setContainsAlco(false);

        doReturn(null).when(tastyCodeOrderResource).orderMainCourse();
        doReturn(drink).when(tastyCodeOrderResource).orderDrink();
        doReturn(DrinkAdditions.ICE.toString()).when(tastyCodeOrderResource).drinkAdditionRequest();

        String input = "Aleksandra\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        tastyCodeOrderResource.scanner = new Scanner(inputStream);

        tastyCodeOrderResource.makeAnOrder();

        verify(customerOrderRepository).save(any(CustomerOrder.class));
        verify(tastyCodeOrderResource).orderMainCourse();
        verify(tastyCodeOrderResource, never()).pickDessert();
        verify(tastyCodeOrderResource).orderDrink();

        assertTrue(outContent.toString().contains("Ok Aleksandra, here's your order:"));
        assertTrue(outContent.toString().contains("Water"));
        assertTrue(outContent.toString().contains("Total price = 3.0zł"));
    }

    @Test
    public void testMakeAnOrderNoOrder() {
        doReturn(null).when(tastyCodeOrderResource).orderMainCourse();
        doReturn(null).when(tastyCodeOrderResource).orderDrink();

        tastyCodeOrderResource.makeAnOrder();

        verify(customerOrderRepository, never()).save(any(CustomerOrder.class));
        verify(tastyCodeOrderResource).orderMainCourse();
        verify(tastyCodeOrderResource).orderDrink();
        assertTrue(outContent.toString().contains("Maybe next time..."));
    }
}