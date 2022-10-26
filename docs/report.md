## Devtools. Report for lab2

### 1.

> Запустите локальный веб-визуализатор репозитория и сделайте так, чтобы в нём отображалось нормальное описание
> репозитория.

```shell
# Идем редачить описание репы
vim .git/description
```

```text
> This is new description for the repository!
```

```shell
# Перезапускаем веб-визуализатор и чекаем
git instaweb --httpd=webrick
```

![](imgs/1.png)


### 2.

> Перенесите все коммиты, находящиеся в ветке `ci`, в ветку `master` с объединением всех коммитов в один и изменением сообщения таким образом, чтобы оно полностью описывало все вносимые изменения.
>
> Удалите ветку `ci`.


```shell
git checkout ci

# Чекаем разницу между ветками
git cherry -v master

# Делаем интерактивный ребейз и сквошим второй коммит с первым
# Первый коммит оставляем без изменений
# При желании можем поменять сообщение после сквоша
git rebase -i HEAD~2

git rebase master
git checkout master
git merge ci
git branch -d ci
```

![](imgs/2_1.png)
![](imgs/2_2.png)


### 3.

> В репозитории есть несколько альтернативных историй проекта, недоступных из текущей версии графа и не связанных с ней.
> 
> Найдите последний коммит любой из версий и создайте на нём ветку old-master.

```shell
# Делаем красивую магию и получаем огромный список коммитов
git log --all --decorate --oneline --graph --reflog
```
![](imgs/3_1.png)
```shell
# Можем пикнуть любой отходящий последний коммит
# Например, возмём a9250b1
git checkout a9250b1
git checkout -b old-master
```

![](imgs/3_2.png)


### 4.

> Определите коммит, в котором строчка `32` файла `prisma/seed.ts` изменялась в последний раз, и его дату.

```shell
# Ищем коммит, в котором строчка 32 файла prisma/seed.ts изменялась в последний раз
git blame -L 32,32 prisma/seed.ts
```

```text
> ca0b3575 (Nikolay Andreev 2021-12-20 00:28:11 +0300 32)         rating: game.age_rating_sorting_i,
```

### 5.

> В проекте существует регресс, на который имеется тест, запускающийся по команде `npm run test`.
> 
> Найдите коммит, в котором проявился регресс.

```shell
# Ищем коммит, в котором проявился регресс
git bisect start

# Запускаем тесты 
npm run test

# Получаем картину с ошибкой, маркаем как bad
git bisect bad

# Повторяем такие приколы чередуя bad и good
...
git bisect good
...
# Если коммит опять bad или good, то можно берем следующий
git bisect next

# Пропишем ресет, что не отображалось в логах
git bisect reset
```

![](imgs/5_1.png)
![](imgs/5_2.png)


### 6.

> В репозитории существует файл `.env`, содержащий конфиденциальную информацию.
> 
> Удалите его из всех коммитов, где он присутствует, и добавьте в `.gitignore`.

```shell
# Удаляем .env из всех коммитов, где он присутствует
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch .env" \
  --prune-empty --tag-name-filter cat -- --all
  
# Проверяем, что файл удалён
git ls-tree -r master --name-only | grep .env

# Игнорим, чтобы не попадал в репозиторий
echo ".env" >> .gitignore

# При желании можно было сделать копию с ренеймингом, но лень и добавляем заново
echo "DATABASE_URL=postgresql://blackbox:123456@localhost:5432/postgres" >> .env

git add .
git ci -m 'fix: remove .env files from commits'
```

![](imgs/6_1.png)


#### 7.

> Сделайте так, чтобы автором коммитов в ветке `feature` были Вы.
> 
> Для этого укажите в изменяемых коммитах почту, привязанную к GitHub, и своё ФИО.

```shell
# Переключаемся на ветку feature    
git checkout feature

# Умом изменяем коммиты, указывая почту и ФИО
git filter-branch --force --env-filter "
  GIT_AUTHOR_EMAIL=fadyat@icloud.com;
  GIT_AUTHOR_NAME="fadyat";
  GIT_COMMITER_EMAIL=$GIT_AUTHOR_EMAIL;
  GIT_COMMITER_NAME=$GIT_AUTHOR_NAME;"
  
# В итоге получим две ветки, одна с изменёнными коммитами, другая с оригинальными (бэкап)
```

![](imgs/7_0.png)

Проблема в верхней штуке, что она перезаписывает все дерево коммитов на ветке, а там так же
коммиты мастера, которые не нужно трогать. Поэтому лучше сделать так:
```shell
# Переключаемся на ветку feature
git checkout feature

# Умом изменяем коммиты, указывая почту и ФИО
git config --local user.email "fadyat@icloud.com"
git config --local user.name "fadyat"

# Смотрим в скольких коммитах надо поменять автора, в данном случае 3
git log --oneline --decorate --graph --all

# Перебираем коммиты и меняем автора, пикаем все коммиты
git rebase -i HEAD~3 --exec 'git commit --amend --reset-author -CHEAD'
```

![](imgs/7_1.png)
![](imgs/7_2.png)
![](imgs/7_3.png)

#### 8.

> Включите запоминание разрешений конфликтов.
> 
> Влейте ветку `feature` в `master`, разрешив конфликт при слиянии.
> 
> Откатите слияние, внесите изменение в файл `README.md` и снова влейте ветку feature в master без ручного разрешения конфликта.

```shell
git checkout master
git config --global rerere.enabled true
git merge feature
git rerere status

# Ресолвим конфликты 
vim <conflicted_file>

# Добавляем изменения, и вуаля мердж успешен
git add . && git commit

# Откатываем слияние
git reset --hard HEAD~1

# Делаем мердж без ручного разрешения конфликта
# После увидим сообщение о том, что конфликт разрешен автоматически
# При желании можно посмотреть, что было сделано автоматически = git diff
git merge feature
git add . && git commit
```

![](imgs/8_1.png)
![](imgs/8_2.png)

#### 9.

> Проверьте целостность репозитория и убедитесь, что с ним всё в порядке.
> 
> При наличии ошибок исправьте их.

```shell
git fsck --full
```

![](imgs/9.png)

#### 10.

> Проверьте размер репозитория (папки `.git`) и добейтесь уменьшения его размера.

```shell
# Чекаем до (в отчет не добавил, я глупышка)
du -sh .git

# Оптимизируем
git gc
git fsck --full

# Чекаем после
du -sh .git
```

![](imgs/10_1.png)
![](imgs/10_2.png)
![](imgs/10_3.png)

#### 11.

```text
Из-за того, что я сразу писал все в файл, не получается закоммитить файл по частям тк его не с чем сравнивать
```

```shell
git add --patch <file>
git commit -m 'feat: add new <file> for <task>'
```
