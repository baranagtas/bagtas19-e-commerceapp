# Ecommerce Application

This Spring Boot project provides a comprehensive backend solution for managing an ecommerce platform. It offers functionalities for managing products, customers, carts, and orders efficiently.

## Entities:

- **Product:** Represents a product available for sale in the platform. Includes attributes such as name, description, price, stock quantity, creation date, and last updated date.

- **Customer:** Represents a user registered on the platform. Contains details like name, email, address, etc.

- **Cart:** Represents a shopping cart associated with a customer. Stores the list of products added to the cart and the total price of the cart.

- **Order:** Represents a purchase order placed by a customer. Contains details such as order number, order date, total amount, and status.

## Features:

- **AddCustomer:** Endpoint to add a new customer to the platform.
- **GetProduct:** Endpoint to retrieve details of a specific product by its ID.
- **CreateProduct:** Endpoint to add a new product to the platform.
- **UpdateProduct:** Endpoint to update an existing product's details.
- **DeleteProduct:** Endpoint to delete a product from the platform.
- **GetCart:** Endpoint to fetch details of a customer's shopping cart.
- **UpdateCart:** Endpoint to update the contents of a customer's shopping cart.
- **EmptyCart:** Endpoint to clear all items from a customer's shopping cart.
- **PlaceOrder:** Endpoint to place an order for the items in the customer's cart.
- **GetOrderForCode:** Endpoint to retrieve details of an order by its unique order code.
- **GetAllOrdersForCustomer:** Endpoint to fetch all orders placed by a specific customer.
- **AddProductToCart:** Endpoint to add a product to a customer's shopping cart.
- **RemoveProductFromCart:** Endpoint to remove a product from a customer's shopping cart.

## Usage:

1. Clone the repository to your local machine.
2. Import the project into your preferred IDE.
3. Configure the project settings and database connection in `application.properties`.
4. Build and run the project.
5. Access the provided endpoints using a REST client or HTTP requests.

Feel free to explore and customize the project according to your requirements!
