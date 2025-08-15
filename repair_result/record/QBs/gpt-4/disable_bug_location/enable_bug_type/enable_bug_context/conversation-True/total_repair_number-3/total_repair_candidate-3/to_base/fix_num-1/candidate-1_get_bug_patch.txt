import string
def to_base(num, b):
    result = ''
    alphabet = string.digits + string.ascii_uppercase
    if num == 0:
        return '0'
    while num > 0:
        i = num % b
        num = num // b
        result = alphabet[i] + result
    return result
