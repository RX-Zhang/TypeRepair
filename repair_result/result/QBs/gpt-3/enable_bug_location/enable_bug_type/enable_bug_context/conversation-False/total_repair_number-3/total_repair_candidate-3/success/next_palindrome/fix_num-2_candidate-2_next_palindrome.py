def next_palindrome(digit_list):
    high_mid = len(digit_list) // 2
    low_mid = (len(digit_list) - 1) // 2
    while high_mid > low_mid:
        if digit_list[high_mid] == 9:
            digit_list[high_mid] = 0
            digit_list[low_mid] = 0
            high_mid -= 1
            low_mid += 1
        else:
            digit_list[high_mid] += 1
            digit_list[low_mid] += 1
            return digit_list
    return [1] + (len(digit_list) * [0]) + [1]
