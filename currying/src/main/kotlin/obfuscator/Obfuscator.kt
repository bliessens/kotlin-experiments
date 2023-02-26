package obfuscator

val stringObfuscator = { replace: Regex, replacement: String, input: String ->
    input.replace(replace, replacement)
}

val curriedObfuscator: (Regex) -> (String) -> (String) -> String = stringObfuscator.curried()

fun pwdObfuscator(input: String) = stringObfuscator(".".toRegex(), "*", input)
val curriedPwdObfuscator = curriedObfuscator(".".toRegex()).invoke("*")

