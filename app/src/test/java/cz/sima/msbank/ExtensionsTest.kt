package cz.sima.msbank

import cz.sima.msbank.utils.extensions.countDigitRoot
import org.junit.Assert
import org.junit.Test

/**
 * Created by Michal Šíma on 07.06.2020.
 */
class ExtensionsTest {

    @Test
    fun digitRootTest() {
        Assert.assertEquals(567.countDigitRoot(), 18)
        Assert.assertEquals(3.countDigitRoot(), 3)
        Assert.assertEquals(0.countDigitRoot(), 0)
        Assert.assertEquals(22.countDigitRoot(), 4)
        Assert.assertEquals((-22).countDigitRoot(), 4)
    }
}
