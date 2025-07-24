package co.hoppen.filterimage_skin

import co.hoppen.filterimage.BaseFilter
import co.hoppen.filterimage.FilterType
import co.hoppen.filterimage_skin.filter.SkinHydrationStatus

/**
 * Created by YangJianHui on 2025/7/24.
 */
enum class SkinFilterType(override val clazz: Class<out BaseFilter>) : FilterType {
    SKIN_HYDRATION_STATUS(SkinHydrationStatus::class.java)
}