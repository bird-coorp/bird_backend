package br.com.bird.servicebirdad.domain

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RequiresProfile(val profile: String)
