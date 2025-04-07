package pl.youkidesign.TastyCode.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.youkidesign.TastyCode.entity.CustomerOrder;
import pl.youkidesign.TastyCode.entity.Drink;
import pl.youkidesign.TastyCode.entity.Food;
import pl.youkidesign.TastyCode.entity.Restaurant;
import pl.youkidesign.TastyCode.model.DrinkAdditions;
import pl.youkidesign.TastyCode.repository.CustomerOrderRepository;
import pl.youkidesign.TastyCode.repository.DrinkRepository;
import pl.youkidesign.TastyCode.repository.FoodRepository;
import pl.youkidesign.TastyCode.repository.RestaurantRepository;
import pl.youkidesign.TastyCode.utils.UserInputProcess;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class TastyCodeOrderResource {

    @Autowired
    private UserInputProcess userInputProcess;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    static final String GREETING = "Welcome to TastyCode - a digital restaurant where you can order a delicious binary lunch. \n" +
            "You can always leave the restaurant by typing '0' \n" +
            "What can we serve you today?";
    public static final String TRYAGAIN = "Something went wrong. Try again.";

    public Scanner scanner = new Scanner(System.in);


    @Transactional
    public void makeAnOrder() {
        System.out.println(GREETING);

        double totalPrice = 0;
        String orderDescription = "";
        CustomerOrder newOrder = new CustomerOrder();

        Food orderedFood = orderMainCourse();
        if (orderedFood != null) {
            newOrder.setFoodOrdered(orderedFood);
            totalPrice = orderedFood.getPrice();
            orderDescription = orderDescription + orderedFood.getName() + ", ";

            Food orderedDessert = pickDessert();
            newOrder.setDessertOrdered(orderedDessert);
            totalPrice += orderedDessert.getPrice();
            orderDescription = orderDescription + orderedDessert.getName() + ", ";

        }
        Drink orderedDrink = orderDrink();
        if (orderedDrink != null) {
            newOrder.setDrinkOrdered(orderedDrink);
            totalPrice += orderedDrink.getPrice();
            orderDescription = orderDescription + orderedDrink.getName();

            String extraRequest = drinkAdditionRequest();
            if (!extraRequest.isEmpty()) {
                newOrder.setSpecialRequests(extraRequest);
                orderDescription = orderDescription + "\nSpecial requests: " + extraRequest;
            }
        }

        if (orderedFood == null && orderedDrink == null) {
            System.out.println("Maybe next time...");
            return;
        }

        newOrder.setFinalPrice(totalPrice);
        System.out.println("What is your name?");
        String nameResponse = scanner.nextLine();
        newOrder.setCustomerName(nameResponse);

        customerOrderRepository.save(newOrder);

        System.out.println("Ok " + nameResponse + ", here's your order: \n" +
                orderDescription +
                "\nTotal price = " + totalPrice + "zł" +
                "\nWe will call you when it's ready. Thank you.");
    }


    //ORDERING METHODS----------------------------------

    public Food orderMainCourse() {

        System.out.println("Would you like some lunch? \n [Y/N]");
        if (!userInputProcess.ifOrder()) {
            return null;
        }

        while (true) {

            // pick a restaurant
            System.out.println("What cuisine would you like? \n[Type a number]");

            List<Restaurant> restaurantList = restaurantRepository.findAll();
            for (Restaurant restaurant : restaurantList) {
                System.out.println(restaurant.getName() + ": " + restaurant.getCuisineType() + " - [" + restaurant.getId() + "]");
            }

            long responseRestaurant = scanner.nextLong();
            scanner.nextLine();
            userInputProcess.ifExit(responseRestaurant);

            Restaurant pickedRestaurant = restaurantList.stream()
                    .filter(restaurant -> restaurant.getId().equals(responseRestaurant))
                    .findFirst()
                    .orElse(null);

            if (pickedRestaurant == null) {
                System.out.println(TRYAGAIN);
                continue;
            }

            // pick mainCourse
            System.out.println("Here is our menu of " + pickedRestaurant.getCuisineType() + " cuisine type food: \n[Type a number]");

            List<Food> foodList = foodRepository.findByRestaurantIdAndIsDessert(responseRestaurant, false);
            foodList.forEach(food -> {
                System.out.println(food.getName() + ": " + food.getPrice() + " - [" + food.getId() + "]");
            });

            long responseFood = scanner.nextLong();
            scanner.nextLine();
            userInputProcess.ifExit(responseFood);

            Food pickedFood = getFood(foodList, responseFood);
            if (pickedFood == null) {
                System.out.println(TRYAGAIN);
                continue;
            }

            return pickedFood;
        }
    }


    public Food pickDessert() {
        System.out.println("Our lunch includes a dessert.");

        while (true) {
            System.out.println("Which dessert do you like? \n[Type a number]");

            List<Food> dessertList = foodRepository.findByIsDessert(true);
            dessertList.forEach(food -> {
                System.out.println(food.getName() + ": " + food.getPrice() + " - [" + food.getId() + "]");
            });

            long response = scanner.nextLong();
            scanner.nextLine();
            userInputProcess.ifExit(response);

            Food pickedDessert = getFood(dessertList, response);
            if (pickedDessert == null) {
                System.out.println(TRYAGAIN);
                continue;
            }

            return pickedDessert;
        }
    }


    public Drink orderDrink() {

        System.out.println("Something to drink? \n [Y/N]");
        if (!userInputProcess.ifOrder()) {
            return null;
        }

        while (true) {
            System.out.println("What drink would you like? \n[Type a number]");

            List<Drink> drinkList = drinkRepository.findAll();
            drinkList.forEach(drink ->
            {
                if (drink.isContainsAlco()) {
                    System.out.println(drink.getName() + " (contains alcohol): " + drink.getPrice() + "zł - [" + drink.getId() + "]");
                } else {
                    System.out.println(drink.getName() + ": " + drink.getPrice() + "zł - [" + drink.getId() + "]");
                }
            });

            long response = scanner.nextLong();
            scanner.nextLine();
            userInputProcess.ifExit(response);

            Drink pickedDrink = getDrink(drinkList, response);

            if (pickedDrink == null) {
                System.out.println(TRYAGAIN);
                continue;
            }

            return pickedDrink;
        }
    }

    protected String drinkAdditionRequest() {
        System.out.println("Any special requests for you drink?");
        Arrays.stream(DrinkAdditions.values()).toList()
                .forEach(System.out::println);

        return scanner.nextLine();
    }

    //ITEM COLLECTING----------------------------------

    private Food getFood(List<Food> foodList, Long foodId) {

        return foodList.stream()
                .filter(food -> food.getId().equals(foodId))
                .findFirst()
                .orElse(null);
    }

    private Drink getDrink(List<Drink> drinkList, Long drinkId) {

        return drinkList.stream()
                .filter(drink -> drink.getId().equals(drinkId))
                .findFirst()
                .orElse(null);
    }

}
