class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        //We'll use a for loop
        for (int i=0; i<nums.size(); i++) {
            for (int j=i + 1; j<nums.size();j++) {
                if (nums[j] == target - nums[i]) {
                    return {i,j};
                }
            }
        }
        //It will return empty if no solution is found
        return {};
    }
};