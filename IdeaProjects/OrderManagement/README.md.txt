In this exercise I have chosen the implementation using Java 8 stream api, as it provides inbuild methods for 
grouping, aggregation and sorting. As streams have strong affinity with functions, encourages loose coupling.

The Summary Information, I have retrieved based on OrderType Enum. Purposely created Summary object, so that client
code could retrieve the list and display the information on UI. Could have kept String as return type but 
would be hard in case if client needs to change the details.