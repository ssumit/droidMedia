The LRU Cache here is not optimized. We will soon post the optimized version of the LRU that will use heap. Presently we
spent linear time for every eviction and constant time in upgrading the item time. The heap will decrease the eviction
item search time to constant and the item update time will be O(log n), where n is the number of items in cache.