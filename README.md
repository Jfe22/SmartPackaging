# SmartPackaging
Platform for Smart Packaging Management


enum packType {Primary, Secondary, Tertiary} 

class packEnvironment
	float/int percentLeft
	boolean expired

	float/int temperature
	float/int humidity
	float atmPressure
	string location
	float maxGForce


class SmartPackage
	int id
	PackType type
	String material
	Product product 
	packEnvironment env


class SmartTransportPackage
	int id
	Order order
	SmartPackage[] packages VS Products products[] --
                                                     |
                                                     |
                                    aqui vai depender da implementacao
                                    escolhida para a herenca ou nao das
                                    packages. mandar mail ao prof

class Order
	int id
	Date orderDate
	Date estDeliveryDate
	Products[] products
	..
	...

class Product 
	int id
	String name
	String expireDate
	float weight
	string[] ingredients 
	...
	..


class User 
	int id - (VS username)
	string name
	string password
	string email


class Consumer extends User
	...
	...

class Operator extends User
	...
	...

class Producer extends User
	...
	...

