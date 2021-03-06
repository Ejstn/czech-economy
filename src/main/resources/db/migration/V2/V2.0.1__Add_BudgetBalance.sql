CREATE TABLE budget_balance
(
    year INT NOT NULL CHECK (year >= 1),
    value_millions_crowns BIGINT NOT NULL,
    PRIMARY KEY (year)
);

INSERT INTO budget_balance (year, value_millions_crowns) VALUES
(1993, 1081),
(1994, 10449),
(1995, 7230),
(1996, -1562),
(1997, -15717),
(1998, -29331),
(1999, -29634),
(2000, -46061),
(2001, -67705),
(2002, -45716),
(2003, -109053),
(2004, -93684),
(2005, -56338),
(2006, -97580),
(2007, -66392),
(2008, -20003),
(2009, -192394),
(2010, -156416),
(2011, -142771),
(2012, -101000),
(2013, -81264),
(2014, -77782),
(2015, -62804),
(2016, 61774),
(2017, -6151),
(2018, 2944),
(2019, -28515);
