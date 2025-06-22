def levenshtein(source, target):
    if source == '' or target == '':
        return len(source) + len(target)  # Corrected to return the sum of lengths
    elif source[0] == target[0]:
        return levenshtein(source[1:], target[1:])  # Removed the +1 for matching characters
    else:
        return 1 + min(
            levenshtein(source,     target[1:]),  # Cost of deletion
            levenshtein(source[1:], target[1:]),  # Cost of substitution
            levenshtein(source[1:], target)       # Cost of insertion
        )
