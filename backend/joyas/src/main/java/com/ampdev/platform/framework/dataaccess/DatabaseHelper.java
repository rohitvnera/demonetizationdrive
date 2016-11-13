package com.ampdev.platform.framework.dataaccess;

import org.hibernate.Criteria;

/**
 * @authour Mouli Mukherjee <moulimukherjee@gmail.com>
 */
public class DatabaseHelper {

	public static void applyPagination(Criteria crit, PaginateBy paginate) {
		if (paginate == null) return;
		int fetchSize = PaginateBy.MAX_FETCH_SIZE;
		if (paginate.getOffset() != null) {
			crit.setFirstResult(paginate.getOffset().intValue());
			if (paginate.getSize() != null) {
				int max = paginate.getSize();

				// range check it.
				if (max < 0 || max > PaginateBy.MAX_FETCH_SIZE) max = PaginateBy.MAX_FETCH_SIZE;

				crit.setMaxResults(max);
				fetchSize = max;
			}
		}
		crit.setFetchSize(fetchSize);

	}

}
