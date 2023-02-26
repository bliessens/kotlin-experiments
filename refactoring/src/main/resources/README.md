### What is bad about htis class?

#### Usage of Optional<String>

Optional conveys: a value or no value  (better than "a value or NULL")
The value wrapped by Optional is generally "the good case" we are after. The empty
Optional is the bad case.

`ConnectUtil.getConnectorStatus` returns a `Optional<String>` but is that the "good" case?

Can the thrown IOException be reduced to Optional.of(..) or Optional.empty() to simplify things? 



