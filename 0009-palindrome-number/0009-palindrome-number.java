class Solution {
    public boolean isPalindrome(int x) {
        //starting with a if statements because we need to know if it will work
        if ((x<0)){
            return false;
        }
        int original=x;
        int rev=0;

        //Making sure x is not equal to 0

        while(x!=0){
            int digit=x%10;

            if (rev>Integer.MAX_VALUE/10 || (rev==Integer.MAX_VALUE/10 && digit>7)){
                return false;
            }

            rev = rev * 10 + digit;
            x/=10;
        }
        return original == rev;
    }
}