class Solution:
    def romanToInt(self, s: str) -> int:
        import itertools as it
        res = 0
        roman = {
            "I": 1,
            "V": 5,
            "X": 10,
            "L": 50,
            "C": 100,
            "D": 500,
            "M": 1000,
            None: 0,
        }

        for a, b in it.zip_longest(s, s[1:]):
            if roman[a] < roman[b]:
                res -= roman[a]
            else:
                res += roman[a]
        
        return res   