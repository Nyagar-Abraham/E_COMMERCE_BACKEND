# Query.getUserById: User
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  getUserById(id: "random12345") {
    id
    email
    username
    firstName
    lastName
    phoneNumber
    dateOfBirth
    status
    emailVerified
    phoneVerified
    profile
    addresses
    preferences
    createdAt
    updatedAt
    lastLoginAt
  }
}

```