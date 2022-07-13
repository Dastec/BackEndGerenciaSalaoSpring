package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.models.enums.UserStatus
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserModel(

    @Column(name = "id_user", unique = true, updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idUser: Long? = null,

    @Column(name = "user_key",nullable = false, unique = true, updatable = false)
    val userKey: String = UUID.randomUUID().toString(),

    @Column(name = "user_name", nullable = false)
    val userName: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val email: String,

    @Column(name = "verified_email", nullable = false)
    val verifiedEmail: Boolean = false,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(name = "verified_phone", nullable = false)
    val verifiedPhone: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    val beautySalon: BeautySalonModel,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    var roles: Set<Role> = setOf(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: UserStatus = UserStatus.ACTIVE

)