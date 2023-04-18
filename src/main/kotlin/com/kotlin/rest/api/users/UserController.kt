package com.kotlin.rest.api.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.kotlin.rest.api.users.User
import org.springframework.web.bind.annotation.DeleteMapping

@RestController
@RequestMapping("api/users")
class UserController (
    @Autowired
    private val userRepository:UserRepository
)
{
    //GETTING ALL THE USERS
    @GetMapping("")
    fun getAllUsers():List<User> = userRepository.findAll().toList()

    //CREATING A USER
    @PostMapping("")
    fun createUser(@RequestBody user:User): ResponseEntity<User>{
        val savedUser = userRepository.save(user)
        return ResponseEntity(savedUser, HttpStatus.CREATED)
    }

    //GET A SINGLE USER
    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int) : ResponseEntity<User>{
        val user = userRepository.findById(userId).orElse(null)
        return if (user !=null){
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //UPDATE A USER
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user:User): ResponseEntity<User>{
        val existingUser = userRepository.findById(userId).orElse(null)
        return if(existingUser==null){
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else{
            val updatedUser = existingUser.copy(name = user.name, email = user.email)
            userRepository.save(updatedUser)
            ResponseEntity(updatedUser, HttpStatus.OK)
        }
    }

    //DELETE A USER

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable ("id") userId: Int): ResponseEntity<User>{
        if(!userRepository.existsById(userId)){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }



}

