package tech.simter.id.impl.dao.r2dbc

import org.springframework.data.r2dbc.repository.R2dbcRepository
import tech.simter.id.impl.dao.r2dbc.po.IdHolderPo

/**
 * The reactive repository.
 *
 * @author RJ
 */
interface IdRepository : R2dbcRepository<IdHolderPo, String>