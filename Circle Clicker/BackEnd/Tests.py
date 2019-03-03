import unittest
from Player import updateLocation

class testAllMethods(unittest.TestCase):
    def testUpdateLocation(self):
        p = Player("Max", 0, 1, 10 )
        p.updateLocation(2,3)
        self.assertEqual(p.posX, 2)
