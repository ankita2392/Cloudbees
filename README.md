## API

Below is a list of API endpoints with their respective input and output. Please note that the application needs to be
running for the following endpoints to work. 

## API where you can submit a purchase for a ticket

POST http://localhost:8080/api/bookTicket
REQUEST JSON:
{
"to": "France",
"from": "London",
"email": "bob.lastname@example.com",
"pricePaid": 20
}
RESPONSE:
{
"ticketId": 4,
"seatNumber": 2,
"section": "A",
"trainId": 1,
"from": "London",
"to": "France",
"pricePaid": 20.0,
"passenger": {
"passengerId": 1,
"firstName": "Bob",
"lastName": "Last Name",
"email": "bob.lastname@example.com"
}
}

## An API that shows the details of the receipt for the user

GET http://localhost:8080/api/receipt/1

RESPONSE:
[
{
"ticketId": 1,
"seatNumber": 1,
"section": "A",
"trainId": 1,
"from": "London",
"to": "France",
"pricePaid": 20.0,
"passenger": {
"passengerId": 1,
"firstName": "Bob",
"lastName": "Last Name",
"email": "bob.lastname@example.com"
}
},
{
"ticketId": 2,
"seatNumber": 2,
"section": "A",
"trainId": 1,
"from": "London",
"to": "France",
"pricePaid": 20.0,
"passenger": {
"passengerId": 1,
"firstName": "Bob",
"lastName": "Last Name",
"email": "bob.lastname@example.com"
}
}
]


## An API that lets you view the users and seat they are allocated by the requested section

GET http://localhost:8080/api/section?trainId=1&section=A
RESPONSE:
[
{
"passenger": {
"passengerId": 1,
"firstName": "Bob",
"lastName": "Last Name",
"email": "bob.lastname@example.com"
},
"seatNumber": 1,
"section": "A"
},
{
"passenger": {
"passengerId": 1,
"firstName": "Bob",
"lastName": "Last Name",
"email": "bob.lastname@example.com"
},
"seatNumber": 1,
"section": "A"
}
]

## An API to remove a user from the train

DELETE http://localhost:8080/api/remove?trainId=1&passengerId=1

## An API to modify a user's seat
 
PUT http://localhost:8080/api/modifySeat

JSON:
{
"ticketId": 3,
"newSeatNumber": 2
}
