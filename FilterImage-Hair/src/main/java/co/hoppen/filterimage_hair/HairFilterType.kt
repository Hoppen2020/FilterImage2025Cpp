package co.hoppen.filterimage_hair

import co.hoppen.filterimage.BaseFilter
import co.hoppen.filterimage.FilterType
import co.hoppen.filterimage_hair.filter.ScalpOil

/**
 * Created by YangJianHui on 2025/7/24.
 */

enum class HairFilterType(override val clazz: Class<out BaseFilter>) : FilterType {
    SCALP_OIL(ScalpOil::class.java)
}