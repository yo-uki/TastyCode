1.
SELECT c.CountryID, c.Name 
FROM Country c
JOIN City city ON c.CountryID = city.CountryID
GROUP BY c.CountryID, c.Name
HAVING SUM(city.Population) > 400;

2.
SELECT c.Name
FROM Country c
WHERE NOT EXISTS (
    SELECT 1
    FROM City city
    JOIN Building bd ON city.CityID = bd.CityID
    WHERE city.CountryID = c.CountryID);