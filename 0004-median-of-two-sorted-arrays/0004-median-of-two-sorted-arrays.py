class Solution(object):
    def findMedianSortedArrays(self, nums1, nums2):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :rtype: float
        """
        nums3 = nums1 + nums2
        nums3.sort()
        m = len(nums3)
        if m % 2 == 0:
            median = (nums3[m//2 - 1] + nums3[m//2]) / 2.0
        else:
            median = float(nums3[m//2])
        return median