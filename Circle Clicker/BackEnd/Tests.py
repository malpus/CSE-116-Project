import unittest
from Player import updateLocation

class testAllMethods(unittest.TestCase):
    def testUpdateLocation(self):
        Player.updateLocation(2,3)
        self.assertEqual(p.posX, 2)
